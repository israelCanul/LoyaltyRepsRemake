package com.xcaret.loyaltyreps.view.training.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.xcaret.loyaltyreps.adapter.QuestionFragment
import com.xcaret.loyaltyreps.adapter.QuestionsViewPagerAdapter
import com.xcaret.loyaltyreps.data.api.ResponseQuestionChoice
import com.xcaret.loyaltyreps.data.api.VideoQuizQuestion
import com.xcaret.loyaltyreps.data.api.VideoQuizTraining
import com.xcaret.loyaltyreps.databinding.FragmentVideoQuizzTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.training.vm.VideoQuizzViewModel

class VideoQuizzTraining:  BaseFragmentDataBinding<FragmentVideoQuizzTrainingBinding>(){
    override val tagForBar: String
        get() = "VideoQuizzFragment"
    lateinit var _viewModel: VideoQuizzViewModel

    override fun setHeaderFragment()  {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[VideoQuizzViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentVideoQuizzTrainingBinding.inflate(inflater))
        setHeaderFragment()
        observers()

        return binding.root
    }

    private fun setupUi(videoQuiz: VideoQuizTraining) {
        var points: Int = videoQuiz.points
        var videoInstructions = "Ganarás <font color=\"#8f2081\">$points puntos</font> al responder el quiz correctamente.\n"
        videoInstructions += "Lee con atención las preguntas y selecciona la respuesta correcta."
        binding.txtInfoQuiz.text = HtmlCompat.fromHtml(videoInstructions, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.txtTitleQuiz.text = videoQuiz.name

    }

    private fun settingUpUi(questions: List<VideoQuizQuestion>) {
        val fragments: MutableList<QuestionFragment> = mutableListOf()
        for (question in questions){
            fragments.add(QuestionFragment(question,::questionSelected))
        }

        val adapter = QuestionsViewPagerAdapter(fragments, this)
        binding.fpQuizQuestions.adapter = adapter
        binding.fpQuizQuestions.isUserInputEnabled = false
        binding.quizProgress.max = questions.size
        binding.fpQuizQuestions.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.quizProgress.progress = position + 1
            }
        })
    }
    private fun questionSelected(res: ResponseQuestionChoice){
        Log.i("QuestionFragment", "onViewCreated: $res")
    }

    private fun observers() {
        _viewModel.videoQuizTraining.observe(viewLifecycleOwner){
            it?.let{ videoQuiz ->
                videoQuiz.questions.let { questions ->
                    settingUpUi(questions)
                }
                setupUi(videoQuiz)
            }
        }
    }

}